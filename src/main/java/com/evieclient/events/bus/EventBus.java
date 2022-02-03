package com.evieclient.events.bus;

import com.evieclient.Evie;
import com.evieclient.events.CancelableEvent;
import com.evieclient.events.Event;
import com.google.common.reflect.TypeToken;
import io.sentry.Sentry;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Class for holding event subscribers.
 * The main implementation can be found in {@link Evie}
 * <p>You can register an object with the {@code post} method.</p>
 *
 * @since 1.0.0
 **/
@SuppressWarnings({"UnstableApiUsage", "unchecked"})
public class EventBus {
    private final HashMap<Class<? extends Event>, CopyOnWriteArrayList<EventListener>> listeners = new HashMap<>();

    /** Registers an object to the {@link EventBus}.
     * All methods annotated with the {@link EventSubscriber} annotation
     * will be called when that Event is posted.
     * @param any instance of the object you want to register **/
    public void register(Object any) {
        Class<?>[] classes = new Class<?>[] { any.getClass(), any.getClass().getSuperclass() };
        for (Class<?> clazz : classes) {
            TypeToken.of(clazz).getTypes().rawTypes().forEach(m -> {
                for (Method method : m.getDeclaredMethods()) {
                    EventSubscriber annotation = method.getAnnotation(EventSubscriber.class);
                    if (annotation == null) continue;
                    if (method.getParameterCount() == 0) throw new IllegalArgumentException(method.getName() + " doesn't have any parameters!");
                    method.setAccessible(true);

                    Class<? extends Event> event;
                    try {
                        event = (Class<? extends Event>) method.getParameters()[0].getType();
                    } catch (Exception ignored) {
                        Sentry.captureException(ignored);
                        throw new IllegalArgumentException(method.getName() + "'s first parameter is not an event.");
                    }

                    if (!listeners.containsKey(event)) {
                        listeners.put(event, new CopyOnWriteArrayList<>());
                    }
                    listeners.get(event).add(new EventListener(any, method, annotation.priority()));
                    listeners.get(event).sort(Comparator.comparingInt(listener -> listener.getEventPriority().getId()));
                }
            });
        }
    }

    /** Unregisters an object from the EventBus.
     * @param any object to be unregistered from the EventBus **/
    public void unregister(Object any) {
        listeners.values().forEach(it -> it.removeIf(listener -> listener.getInstance() == any));
    }

    /** Unregisters <b>all instances</b> of the specified class from the EventBus.
     * @param aClass class of the object to be removed **/
    public void unregister(Class<?> aClass) {
        listeners.values().forEach(it -> it.removeIf(listener -> listener.getInstance().getClass() == aClass));
    }

    /** Sets the priority of all methods with the specified event
     * in <b>all instances</b> of the specified object.
     * @param any object to change the priority of
     * @param event event
     * @param newPriority the new priority of the method **/
    public void setPriorityOf(Object any, Event event, Priority newPriority) {
        Class<? extends Event> eventClass = event.getClass();
        listeners.getOrDefault(eventClass, new CopyOnWriteArrayList<>()).forEach(listener -> {
            if (listener.getInstance() == any) {
                listener.setEventPriority(newPriority, eventClass);
            }
        });
    }

    /** Sets the priority of all methods with the specified event
     * in <b>all instances</b> of the specified class.
     * @param aClass class of the method to change the priority of
     * @param event event
     * @param newPriority the new priority of the method **/
    public void setPriorityOf(Class<?> aClass, Event event, Priority newPriority) {
        Class<? extends Event> eventClass = event.getClass();
        listeners.getOrDefault(eventClass, new CopyOnWriteArrayList<>()).forEach(listener -> {
            if (listener.getInstance().getClass() == aClass) {
                listener.setEventPriority(newPriority, eventClass);
            }
        });
    }

    /** Posts an event to the {@link EventBus}.
     * This calls every method that is listening for the event in question.
     * @param event event to post **/
    public void post(Event event) {
        if (event != null) {
            boolean cancelable = event instanceof CancelableEvent;
            listeners.getOrDefault(event.getClass(), new CopyOnWriteArrayList<>()).forEach(it -> {
                try {
                    if (cancelable && ((CancelableEvent) event).isCanceled()) return;
                    it.getMethod().invoke(it.getInstance(), event);
                } catch (Exception e) {
                    Sentry.captureException(e);
                    e.printStackTrace();
                }
            });
        }
    }

    /** Sorts all event listeners listening for the specified event by priority.
     * @param eventClass class of event in question **/
    void sort(Class<? extends Event> eventClass) {
        listeners.get(eventClass).sort(Comparator.comparingInt(listener -> listener.getEventPriority().getId()));
    }
}

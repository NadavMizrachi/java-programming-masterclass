# Threads and Runnable

1. Prefer using 'Runnable' when defining task.
2. When defining run() method in you custom thread work, you should run this with .start() and not with .run() because the .run() will be executed on same thread that invoced this. In contrast, the .start() will execute the .run() in new thread as expected.
3. Data that saved on heap will be shared to all threads (like class variables). Variables that are defined in function, are stack variables - each thread has its own stack, so those variables will be od different memory location for each thread ==> not will be shared between threads.
4. Let t1 be thread, and t2 be another thread. If we want that t1 will wait until t2 terminate, we should call inside t1 to: `t2.join()`.

# Synchronization block

4. Use 'synchronized' identifier to function that should be executed in only one thread at a time. Or, use synchronized block on heap object and wrap the critical section code.
5. Notice! When you make method with synchronized keyword, this is same like wrap the whole method code with synchronzied on 'this' object. i.e:
```
public synchronized foo() {
    // YOUR CODE
    }
```

is the same as:
```
public foo() {
    synchronized (this) {
            // YOUR CODE
        }
    }
```
6. "Thread Safe" object is an object that was designed and implemented with synchronized blocks on critical sections in order to let the developers use this object in multiple thread env.
7. If we have Object A, and he has method m1() which is synchronized, and method m2() that is not synchronzied, then method any other thread can execute m2() on this object even when there is other object that executes m1(). But, other thread will not be able to execute m1(), because this method is synchronized. If m2() was also synchronized, and thread1 executes m1(), then other threads will not be able to execute m2(), because now this is also synchronized.
8. **The key point that we need to understand is, that `synchronized` is "points" to some object in the heap. Any other code block that has been wrapepd with `synchronized` block that points also to this object will not be able to run in concurrent.**
8. Inside synchronized function, we can call 'wait()' in order to sleep and let other threads the ability to run. If we want to notify other threads to wake up and try obtain the lock, we will call 'notify()'. (There are two notify functions: 1. notify() = notifies specific thread. 2. notifyAll() notifies all threads. If there are alot of threads, notifyAll() could lead to bad performence.)
9. Threads could suspend in middle of executions, except atomic operations. Atomic operations on Java : 1. Object assigment (Obj obj1 = obj2) 2. In primitive assigement (except from double/long assigement). 3. Read/Write volatile vars.
10. When you want collections for using in multiple threads, check which collection is thread safe! (For example, 'ArrayList' is not thread safe, but 'Vector' is thread safe).
11. synchronzied suffer from some drawbacks: If thread inside blocked when it encounters `synchronized`, it cant be notified. Another drawback, `synchronized` must be in same method block. Another,we canot timeout on the lock. Over then that, there is no order when threads blocked waiting on `synchronized` block (JVM decides the order).

# Concurrent Utils

## ReentrantLocker
1. As we saw, synchronized has drawbacks. It is better to use ReentrantLocker. Use as follow:
```
try {
    reentrantLocker.lock();
    // YOU CODE
} finally {
    reentrantLocker.unlock();
}
```
2. Notive that the `reentrantLocker` instance has to be shared among the threads that make tha calls.
3. After calling to `.lock()`, the locker will be owned by the thread. **This is our responsibility to unlock the locker by calling `.unlock()`.**
4. Using this mechanism, we can spread the locking to more then one method.
5. We can run code that will be executed if the locker that we want is locked. Example:
```
if (reentrantLocker.tryLock()) {
        try {
            // YOUR CODE
        } finally {
            this.bufferLocker.unlock();
        }
} else {
    // CODE HANDLES WHEN LOCK IS UNAVAILABLE
}
```

## Thread Pool
1. So far we created Threads manually (`New Thread` or `New Thread(Runnable)`). Creating thread is time consuming task, so when we have to init a lot of threads, it is best practice to use ThreadPools. Thread Pool is a pool of threads with fixed number of threads that JVM creates in order to increase performence. In the follow example we create pool of 2 threads and execute them. We try to execute 3 tasks, so the third will wait until one of the other tasks will terminate. After, we shutdown the pool (after the tasks will be terminated)
```
ExecutorService executorService = Executors.newFixedThreadPool(2);

executorService.execute( Runnable );
executorService.execute( Runnable );
executorService.execute( Runnable );

executorService.shutdown()
```
4. If we want to use thread that will return a value, we will use `Callable` + `executor.submit()` method. Example:
```
Future<String> futureString = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "FUTURE STRING !";
            }
        });
```
5. Then, if we want to retreive the string, we will call `.get()` method (notice, this will be block the current calling thread until the value will be available):
```
try {
    String valueFromFuture = futureString.get()
} catch (Exception) {

}
```
6. In `java.util.concurrent` there are thread safe collections that we can call to their API in multiple thread and all the synchronization is already implemented and encapsulated in them.

## Atomics
* Atomic operation is an operation that will be execute 100% without context switch. If thread executes atomic operation we can be sure that it will be executed fully. The atomic operations on Java is: Assigment of reference object, like : `Object o1 = o2;`. Also. assigment of all primitive type except long and double.
* The operation of `counter++` is actualy has 3 parts: 
    1. Read `counter` value 
    2. Increment the value of `counter` by one. 
    3. Write the sum in `counter`.

    So in order to make this operation atomic we can of course use lockers and synchrinized as we saw. But, in the package `java.util.concurrent.atomics` we can use Integer/ Boolean or other types that will be atomic. There are also classes for long and double. Those classes has atomic operations for increment() or decrease(), and set(). Those classes could be very useful when dealing with concurrent programming and modify counters/values. For more info, read the docs of `java.util.concurrent.atomics` in https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/atomic/package-summary.html.

# Deadlocks
* Deadlock is scenario when two threads waiting for locker that the other thread holds, and therefore they wait forever.
* The best practice to avoid deadlock is 1. Trying to use one locker. 2. If using more then one locker, wait for the lockers in same order in all threads.

# Starvation
* Sometimes, when there is few threads, it could be that one of the threads will not be given enough time to execute because other threads has "better" priority.
* If we use `ReentrantLocker` locker, we can set the locker policy with "fairness" which means that the threads that try to get the locker will get the locker in round rubin fashion (or near to this). In order to make this, we need to pass `true` to the `ReentrantLocker` constructor. Notice! this mechanism could reduce the performence if there are a lot of threads because it has to consider all the threads execution.
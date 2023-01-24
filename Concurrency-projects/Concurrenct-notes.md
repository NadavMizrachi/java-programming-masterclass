# Notes
1. Prefer using 'Runnable' when defining task.
2. When defining run() method in you custom thread work, you should run this with .start() and not with .run() because the .run() will be executed on same thread that invoced this. In contrast, the .start() will execute the .run() in new thread as expected.
3. Data that saved on heap will be shared to all threads (like class variables). Variables that are defined in function, are stack variables - each thread has its own stack, so those variables will be od different memory location for each thread ==> not will be shared between threads.
4. Use 'synchronized' identifier to function that should be executed in only one thread at a time. Or, use synchronized block on heap object and wrap the critical section code.
5. "Thread Safe" object is an object that was designed and implemented with synchronized blocks on critical sections in order to let the developers use this object in multiple thread env.
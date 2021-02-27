# AIEdemo
AIE demo

Project : Create a simple TODO Android app using MVVM architecture. The app allows user to create a task, edit and delete in offline mode. No two tasks with same name can exist at the same time and dates cannot be set in the past. A task contains the task name, task date and task creator (just a string for the users name). The application will store this locally using the android Room library and a background service must be created that notifies users (using android native notifications) if the application has internet access or is in offline mode. LiveData must be used to show data onto the UI.


=> Used MVVM, Kotlin, LiveData, Room, Coroutines

<img src="https://github.com/nakshatra-bazukaa/Noted/blob/master/Noted%20icons/0.75x/Asset%204ldpi.png" align="right" height="150" width="150">

# Noted - A note taking app [![Generic badge](https://img.shields.io/badge/Noted-Download-<COLOR>.svg)]()

# Project Description
1. It is an open-source Android application that allows people to take notes, app code is implemented in Java.
2. An android app implementing room persistence library and live data.
3. App architecture - MVVM architecture.
4. It has many cool gesture features to handle the created notes in a fun way, like swipe to delete and long press to restore notes from trash.
5. The user can generate a note by providing title and description with a very user-friendly UI.
6. Users can choose to delete saved notes by swiping them left or right when required.
7. Deleted notes are transferred to trash and the user can restore them by long pressing on them or delete them from there permanently by swiping them.

# Download the App
[![Generic badge](https://img.shields.io/badge/Noted-Download-<COLOR>.svg)]()

# Project Structure
```bash
.
└── bazukaa
    └── nakshatra
        └── noted
            ├── adapter
            │   ├── NoteAdapter.java
            │   └── TrashNoteAdapter.java
            ├── db
            │   ├── dao
            │   │   ├── NoteDao.java
            │   │   └── TrashNoteDao.java
            │   ├── entity
            │   │   ├── Note.java
            │   │   └── TrashNote.java
            │   ├── NoteDatabase.java
            │   └── TrashNoteDatabase.java
            ├── repository
            │   ├── NoteRepository.java
            │   └── utils
            │       ├── NoteAsyncTask.java
            │       └── TrashNoteAsyncTask.java
            ├── ui
            │   ├── displaynotes
            │   │   └── NotesActivity.java
            │   ├── displaytrashnotes
            │   │   └── TrashNotesActivity.java
            │   ├── makeeditnote
            │   │   └── MakeEditNoteActivity.java
            │   └── Splash.java
            ├── viewmodel
            │   ├── NoteViewModel.java
            │   └── TrashNoteViewModel.java
            └── viewmodelhelper
                ├── NoteViewModelHelper.java
                └── TrashViewModelHelper.java

```

# Screenshots

<img src="https://github.com/nakshatra-bazukaa/Noted/blob/master/SS/Screenshot_20200629-221403%7E2.png" height="360" width="250">
<img src="https://github.com/nakshatra-bazukaa/Noted/blob/master/SS/Screenshot_20200629-221702%7E2.png" height="360" width="250">
<img src="https://github.com/nakshatra-bazukaa/Noted/blob/master/SS/Screenshot_20200629-221706%7E2.png" height="360" width="250">
<img src="https://github.com/nakshatra-bazukaa/Noted/blob/master/SS/Screenshot_20200629-221733%7E2.png" height="360" width="250">
<img src="https://github.com/nakshatra-bazukaa/Noted/blob/master/SS/Screenshot_20200629-221810%7E2.png" height="360" width="250">

# Contributions
Contributions are welcome.
1. Submit an [issue](https://github.com/nakshatra-bazukaa/Noted/issues) describing your proposed fix or feature.
2. If your proposed fix or feature is accepted then, fork, implement your code change.
3. Ensure your code change follows the **standard code style and structure**.
4. Ensure your code is properly tested.
5. Ensure your commits follow the **standard commit message style**
6. Submit a pull request.

## Installation
Clone this repository and import into **Android Studio**
```bash
git clone https://github.com/nakshatra-bazukaa/Noted.git
```

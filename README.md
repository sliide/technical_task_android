# Sliide Android developer challenge 

Below I'm sharing some highlight w.r.t to this task:

####  Note:
- Not handling error response cases where email already exists or not found (only code 201 & 204 is handled as per requirements)
- To show injection of interface between recyclerview and adapter I've avoided using databinding from xml where onclick of view can be binded directly (as an alternate option).
- Removed MyViewModelFactory (ViewModelProvider.Factory) class as we are using @HiltViewModel
- To show db integration i've used roomdatabase. The purpose is to fetch data from api, store in db , fetch from db and populate in UI for user. No offline supports as add/create user won't be meaningful.
- Due to above, add / delete livedata can emit more than once as there are operation's on remote and local data source both together.
- Added unit test for viewmodel and basic ui test to verify ux.

#### Fix incase you get plugin error (like below):

 An exception occurred applying plugin request [id: 'com.android.application']
 > Failed to apply plugin 'com.android.internal.application'.
    > Android Gradle plugin requires Java 11 to run. You are currently using Java 1.8.
      You can try some of the following options:
        - changing the IDE settings.
        - changing the JAVA_HOME environment variable.
        - changing `org.gradle.java.home` in `gradle.properties`.

 Change Gradle JDK
 Goto => AndroidStudio > Preferences > Build Tools > Gradle >  Look for Gradle JDK and Select `Embedded JDK` from dropdown.
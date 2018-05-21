# veggie
A music bot for Discord with a web interface written using the Spring framework. The latest release can be seen [here](https://veggie.pw).

## Usage
### Set environment variables
Environment variables containing a Discord client ID and a client secret must be set for OAuth2 authentication. Also, an environment variable containing your bot token must be set.
* ```VEGGIE_CLIENT_ID=XXXXXXXXXXXXXXXXXX```
* ```VEGGIE_CLIENT_SECRET=XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX```
* ```VEGGIE_BOT_TOKEN=XXXXXXXXXXXXXXXXXXXXXXXX.XXXXXX.XXXXXXXXXXXXXXXXXXXXXXXXXXX```

### Run Gradle task
To run locally:
```
./gradlew bootRun
```
To build a JAR:
```
./gradlew bootJar
```
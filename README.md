# ![Image of our Solution](diary/onair.png)[ON AIR] until Jan. 17th - Star us ![Image of our Solution](diary/logoVotesapp.png)
The project is started for the [Spring Boot Contest](https://github.com/learning-spring-boot/contest). To support us please

* "Star" the Repository and/or
* Tweet about it using the #VotesApp HashTag

It is submitted by [@d0x (Christian Schneider)](https://github.com/d0x) and [@walery (Walery Strauch)](https://github.com/walery). If you have any ideas or feedback, contact us on Twitter or open a ticket on github.

During the contest we write a small [diary](https://github.com/s2team/contest#the-votesapp-diary) and maintain a [trello board for this project](https://trello.com/b/VzijoLhr/votesapp).
If you are really interested in recent updates, you *could* add this `crontab` :)

```bash
crontab -l | { cat; echo "0 13 * * * firefox http://votesapp.de/articles.html"; } | crontab -
```

Thank you for any kind of support! - s2.

# About VotesApp [![Build Status](https://travis-ci.org/s2team/votesapp.svg?branch=master)](https://travis-ci.org/s2team/votesapp)
**VotesApp** is a *WhatsApp* Bot that helps you and your friends to organize Votes in *WhatsApp* groups.

As *WhatsApp* User the probability that you have a group together with some friends is quite huge. If not try it, it's a great feature. And when you are in a group like, it will happend that someone asks a question like, *Who likes to join Karting on Saturday?*, or *Whats about pizza today after work?*. Maybe you have such voting once a week for a fixed event like playing soccer in the evinig? Then it becomes quite hard to manage this.

Now you just invite **VotesApp** into your *WhatsApp* group.
Once joined, **VotesApp** will listen to the Keywords, `in`, `out`, `yes`, `no` to count votes and `status` to give the names of the people which voted for this keyword.

# Technology Stack
**VotesApp** is divided into the following three major backend stacks:

* **VotesApp**:
It's purpose is to communicate via ReST with the **Yowsup-Rest** backend to send and receive WhatsApp messages.
Inside the Application we put our logic behind an own `Plugin` API (blue).
That enables us to add more and more features over the time.
We utilize Reactor (gray) to pass messages forth and back to the Plugins.
As persistance layer we choose mongodb. This layer can runs as Standalone Application by default.
To integrate it with the others the `Yowsup`-profile needs to be active.

* **[Yowsup-Rest](https://github.com/s2team/yowsup-rest)**:
Thats a Spring Boot ReST-Service which abstracts all the Yowsup communication behind the two Resources `/inbox` and `/outbox`.

* **[Yowsup](https://github.com/s2team/yowsup)**:
Yowsup is a really cool 3rd Party Framework to communicates with WhatsApp.
On-Top of yowsup we put a "File" Layer to controll it from **Yowsup-Rest**.
Why Files? See [our diary](http://votesapp.de/10-01-2015/Python_brings_us_back_to_the_basics/).

![Image of our Solution](diary/architecture.png)

We submit the VotesApp stack to the SpringBoot Content only.

# VotesApp Diary

To keep Readme clean, we moved the diary to: http://votesapp.de

# Highlights

This section describes some highlights of our Backend.

* Diary
* Extensibility with Plugins
* Developer "friendliness"
* @Value(#":}{}$:"#$@}#$!$), - em how was it?
* Environment Variables
* Cough, dude, I need some meds. Really, check my health!
* Profile Configuration

## Diary
From day zero we tried to put our experience during the contest into a diary.
Today it has more then 10 entries describing the fun we had.

## Extensibility with Plugins
During the contest we had a lot of ideas what else can be done with this.
Okay, ... the most important [Chuck Norris Plugin](https://github.com/s2team/votesapp/blob/master/src/main/java/de/votesapp/commands/plugins/ChuckNorrisCommandPlugin.java) is done.
But there are much more, - like the *Rock-paper-scissors*, *Cinema Moves*,
*Random Cats Videos* (@Kathy, this one was for you <3) or more advanced votings.

For those we provide this interface:

```java
public interface CommandPlugin {
  public abstract Optional<Answer> interpret(final GroupMessage message, final Group group);
}
```

In the simplest way it can be implemented like this:

```java
@Service
public class PingCommandPlugin extends TextEqualsWordPlugin {

  public static final String[] DEFAULT_RESETS = { "ping", "ping?" };

  public PingCommandPlugin() {
    super(DEFAULT_RESETS);
  }

  @Override
  public Optional<Answer> matches(final GroupMessage message, final Group group) {
    return Optional.of(Answer.intoGroup(group, "Pong!"));
  }
}
```

To see more about Plugins, you can checkout [our Diary post](http://votesapp.de/15-01-2015/Make_it_fucking_delightful/) about it.

## Developer "friendliness"
Nobody likes to have tons of configurations to do before start hacking, right?

So we took care that the application can be started without huzzle (excpect lombok...).
Services like MongoDB and Yowsup which aren't usable by default will mocked by "runnable" replacements.
This is done by utilizing the `@Profile` annotations.
When the `default` profile is loaded, [fongo](https://github.com/fakemongo/fongo) is used to replace the real MongoDb
and our `WebClient` replaced all the Yowsup Python stuff.

To use Fongo we only need to import `compile("com.github.fakemongo:fongo:1.5.9")` and provide this neat configuration:

```java
@Profile("!mongo")
@Configuration
static class MongoDbInMemoryConfiguration extends AbstractMongoConfiguration {

  @Override
  public MappingMongoConverter mappingMongoConverter() throws Exception {
    final MappingMongoConverter mappingMongoConverter = super.mappingMongoConverter();
    // WhatsApp Keys are containing dots in domains.
    mappingMongoConverter.setMapKeyDotReplacement("_");
    return mappingMongoConverter;
  }

  @Override
  protected String getDatabaseName() {
    return "test";
  }

  @Override
  public Mongo mongo() {
    return new Fongo("mongo").getMongo();
  }

  @Override
  protected String getMappingBasePackage() {
    return "de.votesapp";
  }
}
```

Please give them some fame on Github. They are doing really great!

The `WebClient` is also activated by the same technique.
There we used the `@Profile("!yowsup")` annotation.

Once you configured both, you can active them with `--spring.profiles.active=yowsup,mongo`, or you just don't :).

## @Value(#":}{}$:"#$@}#$!$), - em how was it?
Okay, if you really use it all the day,
you probably know the syntax (because you understand whats going on).
But if I work with some Students or ppl. new to Spring, they really create every combination of parentheses,
dollars, braces and so on.

With Spring Boot those @ConfigurationProperties` can be used.

![Image of our Solution](diary/highlightConfig.png)

Just create a counterpart of your configuration structure (yml) and it can be autowirred in your application.
That is so nice!

## Environment Variables
Our P-System is deployed using docker.
From there we don't really like to edit property files.
To get rid of them we use environment variables for the mandatory properties.

This makes it also handy to create various Eclipse 8Run Configurations* and an easy deployment.

![Image of our Solution](diary/highlightEnvVars.png)

## *Cough*, dude, I need some meds. Really, check my health!
The most critical (and not by AutoConfiguration monitored) Module in VotesApp is the communication to the Yowsup-Rest Server.
If this services isn't available, VotesApp can't work. Tracking it was really easy with SpringBoot.

Since we Poll for new message all day long, we can use this to detect the health.

We store exceptions, when they occur in an `Optional` field and anounce them over Springs `HealthIndicator`.

![Image of our Solution](diary/highlightHealth.png)

## Profile Configuration
We really like to feature to have properties per Profile.
With this we were able to separate the configuration for mongodb, yowsup and our application.

![Image of our Solution](diary/highlightProperties.png)

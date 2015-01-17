# ![Image of our Solution](diary/onair.png)[ON AIR] until 17.01.2015 -  √ote for s2 ![Image of our Solution](diary/logoVotesapp.png)
The project is started for the [Spring Boot Contest](https://github.com/learning-spring-boot/contest). To support us please

* "Star" the Repository and/or
* Tweet about it using the #VotesApp HashTag

It is submitted by [@d0x (Christian Schneider)](https://github.com/d0x) and [@walery (Walery Strauch)](https://github.com/walery). If you have any ideas or feedback, contact us on Twitter or open a ticket on github.

During the contest we write a small [diary](https://github.com/s2team/contest#the-votesapp-diary) and maintain a [trello board for this project](https://trello.com/b/VzijoLhr/votesapp).
If you are really interested in recent updates, you *could* add this `crontab` :)

```bash
crontab -l | { cat; echo "0 13 * * * firefox https://github.com/s2team/contest#the-votesapp-diary"; } | crontab -
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

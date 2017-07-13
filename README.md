# WebHookService
A proof of concept implemented with Spring Boot that: 

* Accepts Posts from GitHub outbound WebHooks for events configured in the GitHub Repository.
* Uses the 'X-Hub-Signature' header for security.
* Posts the events to incoming WebHooks on Slack.

#
Configuration for Github
* click on Settings/WebHooks to add a Webhook for the repository you would lke to receive events from.
* Enter a salt value in the 'secret' field.
* Select the events you wish to receive notifications for.

#
Configuration for Slack
* From the Left hand menu, click on 'Configure Apps'
* From the upper menu bar, click on 'Build'
* Click on 'Start building'
* From the Create a Slack App, enter your app name and select your Slack Team.
* Select Incoming Webhooks
* Add a new Webhook and select the Channel you want.
* Copy the Webhook URL - this is the URL you'll use to post to.

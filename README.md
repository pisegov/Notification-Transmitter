# Notification Transmitter Android App

An application for transmitting notifications from an Android device to
a [Telegram bot](https://github.com/pisegov/Notification-Transmitter-Telegram-Bot)

![1](https://user-images.githubusercontent.com/58353454/159916036-b23015ab-5796-46a6-8e39-52cf8b66561a.png)

## Purpose

* The application was created to solve my personal problem: sI always get stuck in social networks
* I wanted to remove the apps of some of them from my phone, but not lose the ability to catch posts
  sent to me by my friends
* The app also allows to catch messages with some login codes sent to an alternate phone number

## How it works

* The Android application is installed on a spare Android phone
* The phone is connected to the internet
* The app has been granted permission to access notifications
* Social network applications and transmitter app are running on the phone with battery saving
  turned off
* When a notification arrives, the application catches it and sends an http request with the
  notification text to a remote server with a running Telegram bot
* The Telegram bot is sending message with response data

## Features

* Based on MVVM architecture pattern
* Working on single activity and uses fragments
* Saves displaying information on configuration change using LiveData
* Also uses
    * RecyclerView to show caught notifications list
    * Retrofit library to send http requests
    * Notification Listener Service to handle notifications and Broadcast Receiver to exchange
      information with other app components
* It currently handles notifications from these apps:
    * Instagram (default app and light version)
    * TikTok
    * Message app
    * Google dialer

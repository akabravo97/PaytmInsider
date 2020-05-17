

[![Assingment badge](https://img.shields.io/badge/Paytm%20Insider-1.0.0-yellowgreen)](https://insider.in/)


This application fetches best experiences happening out there in your city using [Insider Homepage API](https://api.insider.in/home?norm=1&filterBy=go-out&city=mumbai
).

[![Assingment badge](https://j.gifs.com/gZ915k.gif)](https://insider.in/)

# Features!

  - Select out of 3 cities(Mumbai, Hyderabad and Delhi)
  - Subscribe or Unsubscribe from events
  - Sort events by popularity,featuring, price, date
  - Get notified before event starts

## Architecture
It is based on MVVM architecture which has been recommended by Google. It is largely based on principle of

>Separation of concerns, or SoC, is a principle of software design that code be separated into layers and components that each have distinct functionality with as little overlap as possible.


![MVVM Image](https://i.ibb.co/55y7K0m/mvvm-insider.jpg)]

The implementation of above architecture is done in following manner,

![MVVM Image](https://i.ibb.co/0Y0sM5N/mvvm-this-app.jpg)]

### Tech Stack

This app uses a number of open source projects to work properly:

* [Volley](https://developer.android.com/training/volley) - Volley is an HTTP library that makes networking for Android apps easier and most importantly, faster.
* [Dagger 2](https://dagger.dev/) - Dagger is a fully static, compile-time dependency injection framework for both Java and Android.
* [Room](https://developer.android.com/topic/libraries/architecture/room) - Room provides an abstraction layer over SQLite to allow easy database access.
* [ButterKnife](https://github.com/JakeWharton/butterknife) - Field and method binding for Android views which uses annotation processing to generate boilerplate code
* [Glide](https://github.com/bumptech/glide) - Glide supports fetching, decoding, and displaying video stills, images, and animated GIFs. 
* [PageIndicatorView](https://github.com/romandanylyk/PageIndicatorView) - PageIndicatorView is light library to indicate ViewPager's selected page.


### Dagger Depedency Graph
* From Application to Activities
[![MVVM Image](https://i.ibb.co/zFbBPNb/application-activity.png)](https://imgur.com/a/42LotIc)
* Splash Activity
[![MVVM Image](https://i.ibb.co/XDwTpGH/splashactivity.png)](https://imgur.com/a/RE3bjfs)
* City Activity
[![MVVM Image](https://i.ibb.co/R6719L6/cityactivity.png)](https://imgur.com/a/FJ97Xo5)
* Events Activity(including all it's fragments)
[![MVVM Image](https://i.ibb.co/qC2czyF/eventactivity.png)](https://imgur.com/a/G8LIxez)

### Few issues
* In the API response some of the events inside JSON key ```featured``` and ```popular``` were having no ```min_price``` attribute(Free events have ```min_price``` as 0). I have ignored the insertion of these events in local cache.
* In API response ```featured``` events have a higher ```popularity_score``` than ```popular``` events which is causing minor inconsitencies in showing ```Popularity Rating``` in ```EventDetailsFragment```

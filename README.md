# Recommendy

This repository contains the code for an Android application for entertainment recommendations.

## App Overview:
Recommendy is an app that allows you to search for recommendations of entertainment similar to things you already like.  You can input a movie, book, show, artist, or podcast name and Recommendy will suggest similar movies, books, shows, podcasts, or songs.  If you like a recommendation, you can save it to your favorites and come back to it later.

## Features:
- Recommendations provided by the [TasteDive API](https://tastedive.com/read/api)
- Query options including search item and type of results to display (books, movies, any, etc.)
- Results presented in a RecyclerView
- Details about each recommendation include type (displayed as icon), name, description, Wikipedia and YouTube linkes if available
- Ability to save recommendations to your favorites and remove them later (using a Room database)
- User preferences where you can input your name and select how you want your favorites sorted (by type, title, both, or no sorting)
- Ability to share a recommendation with a friend via SMS messaging

## Instructions:
- To use this application you will need to obtain an API key for the TasteDive API and insert it into line 36 of the ResultsActivity
- The MainActivity screen allows you to click buttons to view your saved favorites (will be empty on initial launch), or search for new recommendations
- If you click to start a new search, an AlertDialog will be presented allowing you to input the search item (ex: "Avatar") and select what type of results you would like (from dropdown)
- When you click search, if your phone is connected to the internet, an API call to TasteDive API will be performed 
- The ProgressBar will indicate that the query is being performed, if there are recommendation results they will be dispalyed, or a Toast will say "No resuls found."
- The icon next to each result indicates it's type
- From the ResultsActivity, you can click on the "Info" button of any recommendation to see more info, or you can click on the buttons to driectly view the Wikipedia or YouTube for the recommendation (in a web browser)
- If you click "Info" you will see the description of the recommendation.
- From the DescriptionActivity you can save the recommendation to your favorites, remove it from your favorites, go to your favorites, or go home (to start a new search)
- The FAB on the DescriptionActivity allows you to share the recommendation with a friend. Clicking this button will take you to your selected SMS messaging app and will preset a message to send.
- The FavoritesActivity is a RecyclerView similar to the ResultsActivity, but the icons are stars (indicating favorites). The button options are the same.
- To change your user settings, click on the gear icon in the top right corner of any activity. This will take you to the SettingsActivity
- In the SettingsActivity, you can set your user name (which will display on the FavoritesActivity), and you can select how you want your favorites to be sorted.
- Clicking back from the settings screen will save the preferences selected.

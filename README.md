# Offline-map
Offline map

<img src="http://ec2-35-162-160-209.us-west-2.compute.amazonaws.com:8080/static_files/28052017/screenshots/offline_map.png" />



This application downloads offline map (tile.zip) from the server to your phone SD card (/osmdroid/tile.zip).</br>
It also looks at (/smit/mapped_locations.json) folder of your phone SD card. The json is used to place coordinates, so that map can put the markers on the map at specified cordinates with info:

JSON EXAMPLE:
```
[{"latitude": "59.436962", "longitude": "24.753574",
"description": "Eesti pealinn. Kõige tihedamini asustatud linn eestis",
"subdescription": "Tallinn 59.436962, 24.753574"},
{"latitude": "58.378025", "longitude": "26.728493",
"description": "Ülikooli linn. Tartu ülikool on Euroopas üks parimaid ülikoole",
"subdescription": "Tartu 59.436962, 24.753574"}]
```

Map tile.zip is created Using Mobile Atlas Creator(MOBAC)</br>
<img src="http://ec2-35-162-160-209.us-west-2.compute.amazonaws.com:8080/static_files/28052017/screenshots/mobac.png" width="400" height="300" />
</br>
If the map file is created then you need to upload it to server.</br>


<a href="https://github.com/andreasplado/Offline-map/raw/master/app.apk">Download APK</a>

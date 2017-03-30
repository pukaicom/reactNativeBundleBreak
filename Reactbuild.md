#打bundle 命令
react-native bundle --platform android --dev false --entry-file mainReact.android.js --bundle-output app/src/main/assets/mainReact.android.bundle
#打unbunlde 命令
react-native unbundle --platform android --dev false --entry-file mainReact.android.js --bundle-output app/src/main/assets/mainReact.android.bundle

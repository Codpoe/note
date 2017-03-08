function changeTheme(String theme) {
    switch (theme) {
        case 'light':
            break;
        case 'dark':
            break;
        case 'wood':
            $('body').css("background-image", "url(wood.png)");
            break;
        case 'paper':
            break;
    }
}
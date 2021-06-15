package by.treval.telegramtravelbot.constant;

public class ConstantsBotMessage {
    public static final String STAT_MESSAGE = "\uD83D\uDEE9Hello! This is a telegram bot for tourism. Enter a city and get its places with description and rating. \n ❓Enter /help for more information.";
    public static final String HELP_MESSAGE = "❓ Enter the name of the city to get all the places. \nEnter /all to get a list of all cities. You can get a list of the best and worst places, as well as all the  places by rating. Use the command in front of the city! \n/top City \n/bad City \n/rating City.";
    public static final String ERROR = "Ooooops! Something went wrong:(";
    public static final String ERROR_NO_CITY = "Please write the name of the city!";
    public static final String ERROR_CITY_NOT_FOUND = "Sorry, the city was not found";
    public static final String ERROR_CITIES_NOT_FOUND = "Sorry, the cities was not found";
    public static final String ERROR_PLACES_NOT_FOUND = " Sorry, places was not found";


    public static final String COMMAND_START = "/start";
    public static final String COMMAND_HELP = "/help";
    public static final String COMMAND_ALL_CITIES = "/all";
    public static final String COMMAND_TOP = "/top";
    public static final String COMMAND_BAD = "/bad";
    public static final String COMMAND_RATING = "/rating";
}

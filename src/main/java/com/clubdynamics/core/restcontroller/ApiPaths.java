package com.clubdynamics.core.restcontroller;

/**
 * Class where the endpoint paths for the REST-API are defined. 
 *  
 * @author Henning Schuetz
 *
 */
public class ApiPaths {
  
  public static final String BASE_PATH = "/clubdynamics/api/v1";
  
  public static final String BASE_PATH_CLUBS = BASE_PATH + "/clubs/";
  
  public static final String BASE_PATH_CLUB_ID = BASE_PATH_CLUBS + "{clubId}";
  
  public static final String LOGIN = BASE_PATH_CLUB_ID + "/login";
  
  public static final String PERSON_CREATE = BASE_PATH_CLUB_ID + "/persons";
}

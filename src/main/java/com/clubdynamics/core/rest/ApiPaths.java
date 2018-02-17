package com.clubdynamics.core.rest;

/**
 * Class where all paths for the REST-API are defined. 
 * @author Henning Schuetz
 *
 */
public class ApiPaths {
  
  public static final String BASE_PATH = "/api";
  
  public static final String BASE_PATH_CLUB = BASE_PATH + "/clubs/{clubId}";
  
  public static final String LOGIN = BASE_PATH_CLUB + "/login";
}

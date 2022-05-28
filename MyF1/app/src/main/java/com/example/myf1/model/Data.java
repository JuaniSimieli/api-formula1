package com.example.myf1.model;

import java.util.List;

public class Data {
    public MRData MRData;

    public class MRData {
        public String xmlns;
        public String series;
        public String url;
        public String limit;
        public String offset;
        public String total;
        public StandingsTable StandingsTable;
        public RaceTable RaceTable;

        public class StandingsTable {
            public String season;
            public List<StandingsLists> StandingsLists;

            public class StandingsLists {
                public String season;
                public String round;
                public List<DriverStandings> DriverStandings;
                public List<ConstructorStandings> ConstructorStandings;

                public class ConstructorStandings {
                    public String position;
                    public String positionText;
                    public String points;
                    public String wins;
                    public Constructors Constructor;
                }

                public class DriverStandings {
                    public String position;
                    public String positionText;
                    public String points;
                    public String wins;
                    public Driver Driver;
                    public List<Constructors> Constructors;

                    public class Driver {
                        public String driverId;
                        public String permanentNumber;
                        public String code;
                        public String url;
                        public String givenName;
                        public String familyName;
                        public String dateOfBirth;
                        public String nationality;
                    }

                }

                public class Constructors {
                    public String constructorId;
                    public String url;
                    public String name;
                    public String nationality;
                }
            }
        }

        public class RaceTable {
            public String season;
            public List<Race> Races;

            public class Race {
                public String season;
                public String round;
                public String url;
                public String raceName;
                public Circuit Circuit;
                public String date;
                public String time;
                public Session FirstPractice;
                public Session SecondPractice;
                public Session ThirdPractice;
                public Session Qualifying;
                public Session Sprint;

                public class Circuit {
                    public String circuitId;
                    public String url;
                    public String circuitName;
                    public Location Location;

                    public class Location {
                        public String lat;
                        //public String long;
                        public String locality;
                        public String country;
                    }
                }

                public class Session {
                    public String date;
                    public String time;
                }
            }
        }
    }
}

package com.example.antenatalcareapp;

public class Urls {
    public static final String IP_URL = "http://172.16.0.203/anc/";
//    public static final String IP_URL = "http://192.168.43.121:8080/2021projects/antenatalcareapp/";
    public Urls() {
    }

    public static final String FIRSTREG_URL = IP_URL+"register.php";
    public static final String LOGIN_URL = IP_URL+"login.php";
    public static final String MOTHERS_LIST = IP_URL+"list_mothers.php";
    public static final String CENTERS_LIST = IP_URL+"get-hospitals.php";
    public static final String REG_URL = IP_URL + "register_mother.php";
    public static final String ADD_TIPS_URL = IP_URL+"add_health_tips.php";
    public static final String TIPS_LIST = IP_URL+"health_tips_list.php";
    public static final String CREATE_APPOINTMENTS_URL = IP_URL+"create_appointment.php";
    public static final String APPOINTMENTS_LIST = IP_URL+"appointments_list.php";
    public static final String MESSAGES_LIST = IP_URL+"message_list.php";
    public static final String SEND_MESSAGE = IP_URL+"send_message.php";
    public static final String MOTHER_LOGIN_URL = IP_URL+"mother_login.php";
    public static final String MY_APPOINTMENTS_LIST = IP_URL+"my_appointments.php";
    public static final String MEDICAL_PERSONALS = IP_URL+"medical_personel.php";
    public static final String DELETE_MOTHER = IP_URL+"delete_mother.php";
    public static final String LOAD_PROFILE = IP_URL+"my_profile.php";
}

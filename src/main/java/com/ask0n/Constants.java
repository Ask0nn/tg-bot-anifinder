package com.ask0n;

import org.telegram.telegrambots.meta.api.objects.InputFile;

public interface Constants {
    //settings
    String USERNAME = "TG USERNAME";
    String API_TOKEN = "TG TOKEN";
    String PREFIX = "/";

    //ani api
    String TOKEN = "ANI API TOKEN";
    String ANILIST_URL = "https://anilist.co/anime/";

    //replies
    String START = "<b>������, � AniFinder (@Ask0nBot).</b>\n" +
            "� ������ ���� ����� ����� ����� ������� ����.\n\n" +
            "��� ������ ����� �������� ����� (�� ����������/�������� �����) � ������� ����������.\n\n" +
            "��� ���������: @Ask0n";
    String NOT_FOUND = "<b>� �� ���� ����� ���� ����� ������. =( </b>";
    String TRY_AGAIN = "<b>������� ����� ����������� �������� �������� �������.</b>";
    String ERROR = "<b>������! ������! ������! =( </b>";
    String NOT_FOUND_COMMAND = "������� �� �������! ������� /help ��� �������.";
    String HELP = "<b>������ ��������� ������:</b>\n" +
            "� /start - ������� �������� ����\n" +
            "� /random - ��������� �����\n" +
            "� /help - ������ ������\n" +
            "��� ������ ����� ������ ������� ��� �������� (�� ����������/�������� �����).";
    String CHOICE = "����������� ��� �����, ������ ������ ����";

    //images
    InputFile START_IMAGE = new InputFile("https://static.zerochan.net/Shinomiya.Kaguya.full.2527457.png");
    InputFile NOT_FOUND_IMAGE = new InputFile("https://static.wikia.nocookie.net/v__/images/5/5f/404_not_found.png/revision/latest?cb=20171104190424&path-prefix=vocaloidlyrics");
    InputFile ERROR_IMAGE = new InputFile("https://cdn141.picsart.com/338169980054201.jpg");
    InputFile NO_IMAGE = new InputFile("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQd5eWeOjIjpXYmobzaxnI7NuP_yBh8P09HD2GFw7EtVnM6NegWjc4EyjUhKyo8S04OEMw&usqp=CAU");
    InputFile TRY_AGAIN_IMAGE = new InputFile("https://i.pinimg.com/originals/49/8c/96/498c96c102b9cfca04cb8bc5c63643a2.jpg");
    InputFile NOT_FOUND_COMMAND_IMAGE = new InputFile("https://cdn.discordapp.com/attachments/569495372204998668/937106823822057502/prostite.png");
    InputFile HELP_IMAGE = new InputFile("https://i.quotev.com/img/q/u/19/4/26/w6p7konwyz.jpg");
    InputFile CHOICE_IMAGE = new InputFile("https://pa1.narvii.com/6019/5f978676a2f3157525dd3695de47fc695350f0f8_hq.gif");
}

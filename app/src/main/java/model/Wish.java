package model;

/**
 * Created by Draghici Stefan on 11.02.2016.
 */
//Our wish model
public class Wish
{
    //A wish will have an id, title, content, record date, constructors, getters and setters
    private String title;
    private String content;
    private String recordDate;
    private int id;

    public int getId()
    {
        return id;
    }

    public void setItemId(int id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getRecordDate()
    {
        return recordDate;
    }

    public void setRecordDate(String recordDate)
    {
        this.recordDate = recordDate;
    }

    public Wish()
    {
    }

    public Wish(int id, String title, String recordDate, String content)
    {
        this.id=id;
        this.title = title;
        this.recordDate = recordDate;
        this.content = content;
    }
}

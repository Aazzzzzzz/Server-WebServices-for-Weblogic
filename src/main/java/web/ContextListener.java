package web;

import dal.InitializeDB;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener
{

    public void contextInitialized(ServletContextEvent servletContextEvent)
    {
        new InitializeDB().Init();
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent)
    {

    }

}
package web;

import org.apache.log4j.Logger;
import org.hibernate.StaleObjectStateException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "HibernateFilter", urlPatterns = {"/*"})
public class HibernateSessionRequestFilter implements Filter
{
    private static final Logger log = Logger.getLogger(HibernateSessionRequestFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException
    {
        log.info("# Start 'HibernateSessionRequestFilter' => doFilter() method");
        try
        {
            // Call the next filter (continue request processing)
            chain.doFilter(request, response);
        }
        catch (Exception exception)
        {
            log.error(String.format( "@ Problem 'HibernateSessionRequestFilter' => problem with doFilter() method :['%s']\n '%s'", exception.getMessage(), exception));
        }
    }

    @Override
    public void init(FilterConfig config) throws ServletException
    {
        try
        {
            log.info("# Start 'HibernateSessionRequestFilter' => init() method");
        }
        catch(Exception ex)
        {
            log.error(ex.getMessage(), ex);
        }
    }
    public void destroy()
    {
        log.info("# Start 'HibernateSessionRequestFilter' => destroy() method");
    }
}

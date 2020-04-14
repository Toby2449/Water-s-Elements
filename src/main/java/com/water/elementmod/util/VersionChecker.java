package com.water.elementmod.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;

/**
 * @author jabelar
 *
 */
public class VersionChecker implements Runnable
{
    private static boolean isLatestVersion = false;
    private static String latestVersion = "";

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() 
    {
        InputStream in = null;
        try 
        {
            in = new URL("https://raw.githubusercontent.com/jabelar/MagicBeans-1.7.10/master/src/main/java/com/blogspot/jabelarminecraft/magicbeans/version_file").openStream();
        } 
        catch 
        (MalformedURLException e) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        catch (IOException e) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try 
        {
            latestVersion = IOUtils.readLines(in).get(0);
        } 
        catch (IOException e) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        finally 
        {
            IOUtils.closeQuietly(in);
        }
        System.out.println("Latest mod version = "+latestVersion);
        isLatestVersion = EMConfig.VERSION.equals(latestVersion);
        System.out.println("Are you running latest version = "+isLatestVersion);
    }
    
    public boolean isLatestVersion()
    {
     return isLatestVersion;
    }
    
    public String getLatestVersion()
    {
     return latestVersion;
    }
}
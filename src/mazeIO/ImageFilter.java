package mazeIO;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class ImageFilter extends FileFilter
{

	@Override
	public boolean accept(File pathname)
	{
		String extension = pathname.getName();
		int i = extension.lastIndexOf('.');
		extension = extension.substring(i + 1).toLowerCase();
		
		if(pathname.isDirectory())
			return true;
		
		if(extension != null && extension.equals("bmp"))
			return true;
		return false;
	}

	@Override
	public String getDescription()
	{
		return "BMP images";
	}
}

package Utility;

public class ListViewItemModel {

public String title;
public int iconRes;
public boolean isHeader;

public ListViewItemModel(String string, int iconRes, boolean header) {
   this.title = string;
   this.iconRes = iconRes;
   this.isHeader = header;
}

public ListViewItemModel(String title, int iconRes) 
{
   this(title, iconRes, false);
}
}
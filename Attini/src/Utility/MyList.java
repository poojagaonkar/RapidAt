package Utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.zevenpooja.attini.HomeFragment;

public class MyList 
{
	private static final String COLORCODE = "colorCode";
	private ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
	public MyList(ArrayList<HashMap<String, String>> mylist)
	{
		this.list = mylist;
	}
	
	public ArrayList<HashMap<String, String>> returnList()
	{
		return list;
	}
    public boolean add(HashMap<String, String> map)
    {
        return list.add(map);
    }

    public void  setColor(String newId, String color)
    {
    
      //  for (HashMap<String, String> m : list)
      //      if (m.containsKey(newId))
          
              ///  m.put(HomeFragment.COLORCODE, color);
                
          
	
        
    }
 public void clear()
 {
   list.clear();	
}
 
 public HashMap<String, String> getPosition()
 {
	for(int i=0;i<=list.size();i++)
	{
		return list.get(i);
	}
	return null;
	 
}
    public String getGroupKey(String key, int i)
    {      
        ArrayList<String> uniqeList = getUniqKeyList(key);
        Collections.sort(uniqeList);
        return uniqeList.size()!=0 ? uniqeList.get(0):"";
           
    }

    public ArrayList<String> getUniqKeyList(String key){
        ArrayList<String> l = new ArrayList<String>();
        for (HashMap<String, String> m : list)
            if(!l.contains(m.get(key)))
                l.add(m.get(key));
        return l;
    }

	public int getSize()
	{
		// TODO Auto-generated method stub
		return list.size();
	}
}

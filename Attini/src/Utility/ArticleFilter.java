package Utility;

import java.util.regex.Pattern;

import com.google.common.base.Predicate;
import com.zevenpooja.attini.News;

public class ArticleFilter implements Predicate<News>
{
    private final Pattern pattern;

    public ArticleFilter(final String regex)
    {
        pattern = Pattern.compile(regex);
    }

    @Override
    public boolean apply(final News input)
    {
        return pattern.matcher(input.getNewsSourceTitle()).find();
    }
}
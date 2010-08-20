package jtweet.web.template;

import java.util.List;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

public class GetBigPic implements TemplateMethodModel {

	@Override
	public Object exec(List arglist) throws TemplateModelException {
		// TODO Auto-generated method stub
		if(arglist.size()!=1)   //限定方法中必须且只能传递一个参数   
        {   
            throw new TemplateModelException("Wrong arguments!");   
        }
		String url = (String)arglist.get(0);
		int extp = url.lastIndexOf(".");
		String biggerImageUrl = url.substring(0, extp - 6) + "bigger" + url.substring(extp);
		return biggerImageUrl;
	}

}

package jtweet.mobile.template;

import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.oro.text.perl.Perl5Util;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

public class TexttoHTML  implements TemplateMethodModel {

	@Override
	public Object exec(List arglist) throws TemplateModelException {
		// TODO Auto-generated method stub
		if(arglist.size()!=1)   //限定方法中必须且只能传递一个参数   
        {   
            throw new TemplateModelException("Wrong arguments!");   
        }
		Perl5Util perl = new Perl5Util();
		String text_e = StringEscapeUtils.escapeHtml((String)arglist.get(0));
		//String temp;

		String url_reg = "s/\\b([a-zA-Z]+:\\/\\/[\\w_.\\-]+\\.[a-zA-Z]{2,6}[\\/\\w\\-~.?=&%#+$*!:;]*)\\b/<a href=\"$1\" class=\"twitter-link\" class=\"web_link\" target=\"_blank\">$1<\\/a>/ig";
		String mail_reg = "s/\\b([a-zA-Z][a-zA-Z0-9\\_\\.\\-]*[a-zA-Z]*\\@[a-zA-Z][a-zA-Z0-9\\_\\.\\-]*[a-zA-Z]{2,6})\\b/<a href=\"mailto:$1\" class=\"web_link\" >$1<\\/a>/ig";
		String user_reg = "s/([\\s|\\.|\\,|\\:|\\xA1|\\xBF\\>|\\{|\\(]?)@{1}(\\w*)([\\.|\\,|\\:|\\!|\\?|\\>|\\}|\\)]?)([\\s]|$)/$1\\<a href=\"\\/m\\/user\\/$2\" class=\"user_link\"\\>@$2\\<\\/a\\>$3 /ig";
		String trend_reg = "s/([\\s|\\.|\\,|\\:|\\xA1|\\xBF\\>|\\{|\\(]?)#{1}(\\w*)([\\.|\\,|\\:|\\!|\\?|\\>|\\}|\\)]?)([\\s]|$)/$1\\<a href=\"\\/m\\/search\\?s=%23$2\" class=\"search_link\"\\>#$2\\<\\/a\\>$3 /ig";
		//String shorturl_reg = "m/(href=\\\"http:\\/\\/(bit.ly|j.mp|ff.im)\\/[\\w\\-]{3,10})\\\"/i";

		String rst = perl.substitute(url_reg, text_e);
		rst = perl.substitute(mail_reg, rst);
		rst = perl.substitute(user_reg, rst);
		rst = perl.substitute(trend_reg, rst);

		/*temp = rst;
		while (perl.match(shorturl_reg, temp)) {
			rst = rst.replace(perl.group(0), "href=\"/expend?u=" + Base64.encode(Base64.encode(perl.group(0).substring(6, perl.group(0).length() - 1).getBytes()).getBytes()) + "\"");
			temp = perl.postMatch();
		}*/
		return rst;
	}

}

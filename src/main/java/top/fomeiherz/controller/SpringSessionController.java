package top.fomeiherz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by fomeiherz on 2019-04-18.
 */
@RestController
@RequestMapping(value = "/spring/session")
public class SpringSessionController {

    @GetMapping(value = "/setSession.do")
    public String setSession(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String value = request.getParameter("value");
        request.getSession().setAttribute(name, value);
        return String.format("Set key: %s, value: %s success.", name, value);
    }

    @GetMapping(value = "/getSession.do")
    public String getInterestPro(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        return String.format("key: %s's value is %s.", name, request.getSession().getAttribute(name).toString());
    }

    @GetMapping(value = "/removeSession.do")
    public String removeSession(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        request.getSession().removeAttribute(name);
        return String.format("Remove key: %s success.", name);
    }
}

package com.maceswinger.net;

import java.util.ArrayList;

import org.magnos.entity.Component;
import org.magnos.entity.Controller;

import com.maceswinger.Rectangle;

public class Register {
	private Register() { }
	public static ArrayList<Component<?>> components = new ArrayList<Component<?>>();
	public static ArrayList<Controller> controllers = new ArrayList<Controller>();
}

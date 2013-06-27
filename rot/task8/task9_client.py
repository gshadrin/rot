#!/usr/bin/env python


import sys
import urllib2
sys.path.append('./gen-py')

from task9 import TaskService
from task9.ttypes import *

from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol


try :

	socket = TSocket.TSocket('localhost', 9090);
	transport = TTransport.TBufferedTransport(socket);
	protocol = TBinaryProtocol.TBinaryProtocol(transport);
	client = TaskService.Client(protocol);

	transport.open();
		
	doc = client.load("http://yandex.ru");

	#$result = preg_match_all('/<a[^>]+href=[^ >]+[^>]*>.*?<\/a>/i',$doc,$found); 
	result = doc.count("href");	
	
	print "link count: ", result;
	
	
	
except InvalidValueException as e :
	print "error: ", e.value


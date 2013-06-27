#!/usr/bin/env python

import sys
import urllib2
sys.path.append('./gen-py')

from task9 import TaskService
from task9.ttypes import *

from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from thrift.server import TServer

users = []

class TaskServiceHandler:
	def __init__(self):
		pass
		#self.log = {}	

	def load(self, url):
		try:
			website = urllib2.urlopen(url)
			website_html = website.read() 
		except urllib2.HTTPError, e:
           		raise InvalidValueException(1, 'wrong url')
		return website_html;
	

handler = TaskServiceHandler()
processor = TaskService.Processor(handler)
transport = TSocket.TServerSocket(port=9090)
tfactory = TTransport.TBufferedTransportFactory()
pfactory = TBinaryProtocol.TBinaryProtocolFactory()

server = TServer.TSimpleServer(processor, transport, tfactory, pfactory)

# You could do one of these for a multithreaded server
#server = TServer.TThreadedServer(processor, transport, tfactory, pfactory)
#server = TServer.TThreadPoolServer(processor, transport, tfactory, pfactory)

print 'Starting the server...'
server.serve()
print 'done.'

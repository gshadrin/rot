#!/usr/bin/env php

<?php 
$GLOBALS['THRIFT_ROOT'] = 'src';

 $GLOBALS['THRIFT_ROOT'] = '/usr/lib/php';  
 

require_once $GLOBALS['THRIFT_ROOT'].'/Thrift/Transport/TTransport.php';  
require_once $GLOBALS['THRIFT_ROOT'].'/Thrift/Transport/TSocket.php';  
require_once $GLOBALS['THRIFT_ROOT'].'/Thrift/Protocol/TProtocol.php';  
require_once $GLOBALS['THRIFT_ROOT'].'/Thrift/Protocol/TBinaryProtocol.php';  
require_once $GLOBALS['THRIFT_ROOT'].'/Thrift/Transport/TBufferedTransport.php';  
require_once $GLOBALS['THRIFT_ROOT'].'/Thrift/Type/TMessageType.php';  
require_once $GLOBALS['THRIFT_ROOT'].'/Thrift/Factory/TStringFuncFactory.php';  
require_once $GLOBALS['THRIFT_ROOT'].'/Thrift/StringFunc/TStringFunc.php';  
require_once $GLOBALS['THRIFT_ROOT'].'/Thrift/StringFunc/Core.php';  
require_once $GLOBALS['THRIFT_ROOT'].'/Thrift/Type/TType.php';  
require_once $GLOBALS['THRIFT_ROOT'].'/Thrift/Exception/TException.php';  
require_once $GLOBALS['THRIFT_ROOT'].'/Thrift/Exception/TTransportException.php'; 

require_once 'gen-php/task9/TaskService.php';
require_once 'gen-php/task9/Types.php';
use task9\TaskServiceClient;
use task9\InvalidValueException;

use Thrift\Protocol\TBinaryProtocol;  
use Thrift\Transport\TSocket;  
use Thrift\Transport\TSocketPool;  
use Thrift\Transport\TFramedTransport;  
use Thrift\Transport\TBufferedTransport;  
use Thrift\Exception\TTransportException;

try {

	if ($argc != 2) {
	    die(PHP_EOL . 'Enter url' . PHP_EOL);
	}

	$socket = new TSocket('localhost', 9090);
	$transport = new TBufferedTransport($socket, 1024, 1024);
	$protocol = new TBinaryProtocol($transport);
	$client = new TaskServiceClient($protocol);

	$transport->open();
		
	$doc = $client->load($argv[1]);

	$result = preg_match_all('/<a[^>]+href=[^ >]+[^>]*>.*?<\/a>/i',$doc,$found); 
	//$result = preg_match_all('/href/i',$doc,$found); 
	
	echo "link count: $result \r\n";
	print_r($found); 
	
	
} catch (InvalidValueException $e) {
	echo $e->error_msg.'<br/>';
}


?>

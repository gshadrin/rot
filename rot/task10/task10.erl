-module(task10).

-export([start/0, worker/0, delivery/0]).
	
worker2(Filenames) ->
	Result = lists:nth(	random:uniform(length(Filenames)), Filenames),	
	io:format("REQUEST: ~s ~n", [Result]),	
	delivery ! {Result, self()},
	receive 		
		{file, Data} ->
			L = integer_to_list(string:len(erlang:binary_to_list((Data)))),
			io:format("LENGTH: ~s ~n", [L]),	
			timer:sleep(5000),
			io:format("LL ~n", []),
			worker2(Filenames)
	end.
	
worker() ->
	delivery ! {self()},
	receive 		
		Filenames -> 							
			worker2(Filenames)
	end.
	
delivery() ->
    receive		
        {PID} ->
            {ok, Filenames} = file:list_dir("."),
			PID ! Filenames,
			delivery();
        {File_Name, PID} ->
            {ok, Data} = file:read_file(File_Name),
			PID ! {file, Data},
			delivery()		
    end.	

start() ->
    register(delivery, spawn(task10, delivery, [])),
	spawn(task10, worker, []).	
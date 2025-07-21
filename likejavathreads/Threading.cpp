// Threading.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include "INCLUDE/threading.h"
#include "INCLUDE/log.h"

#define NUMLIM 50

class A: public IRunnable {
private:
	int m_num;
protected:
	void run() {
		Log::LogMessage(L"Executing %d thread\n", m_num);
	}

public:
	A(int num) { m_num = num; }
};

class B: public IRunnable {
protected:
	void run() {
		int i = 0;
		while (!CThread::currentThread().isInterrupted()) {
			Log::LogMessage(L"Executing B cycle %d\n", i);
			::Sleep(100);
			i++;
		}
	}
};

int main(int argc, char* argv[])
{
	CSimpleThreadPool tp(2, NUMLIM + 1);
	A *a[NUMLIM];
	B *b = new B();
	int i = 0;


	for (i = 0; i < NUMLIM; i++) a[i] = new A(i); 

	tp.startAll();
	tp.submit(b); 
	for (i = 0; i < 20*NUMLIM; i++) {
		tp.submit(a[i % NUMLIM], i % NUMLIM); 
		::Sleep(1);
	}

	::Sleep(10000);
	tp.shutdown();

	delete b;
	for (i = 0; i < NUMLIM; i++) delete a[i]; 
	return 0;
}


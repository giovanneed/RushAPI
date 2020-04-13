#include <ctime>

enum eTimerBase
{
    TIMER_SECS = 1,
    TIMER_MILI_SECS = 1000
};

class cTimer
{
        clock_t startTime;
        clock_t endTime;
    
    public:
        void start()
        {
            startTime = clock();
        }
        void stop()
        {
          endTime = clock();
        }
        long int getElapsedTime(eTimerBase base = TIMER_MILI_SECS)
        {
            return (long int)((double)(endTime - startTime) / (double)CLOCKS_PER_SEC * (double)base);
        }
};



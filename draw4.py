# -*- coding: utf-8 -*-

import matplotlib
import matplotlib.pyplot as plt
import numpy as np

x = [500,1000,1500,2000,2500,3000,3500,4000,4500,5000]
y = [[],[]]
with open("result.txt") as f:
	for line in f:
		y1,y2 = line[:-2].split(',')
		y[0].append(y1)
		y[1].append(y2)



#plt.title(u'time of setcover test')
plt.title(u'aprox-solution size of setcover problem')
plt.xlabel("|X|=|F|=n")
#plt.ylabel("time/ms")
plt.ylabel("|C|")
plt.xlim(0,5000)
plt.plot(x,y[0],label="Greedy")
plt.plot(x,y[1],label="LP")
plt.legend(loc="upper left")
plt.show()
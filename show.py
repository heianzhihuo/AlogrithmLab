# -*- coding: utf-8 -*-
import matplotlib
import matplotlib.pyplot as plt
import numpy as np


def readFromFile(fileName):
	points = [[],[]]
	with open(fileName) as f:
		for line in f:
			x,y = line[:-2].split(',')
			points[0].append(float(x))
			points[1].append(float(y))
	return points

point_all=readFromFile("points.txt")

point1=readFromFile("points1.txt")
point1[0].append(point1[0][0])
point1[1].append(point1[1][0])

point2=readFromFile("points2.txt")
point2[0].append(point2[0][0])
point2[1].append(point2[1][0])


point3=readFromFile("points3.txt")
point3[0].append(point3[0][0])
point3[1].append(point3[1][0])

plt.title(u'凸包展示')

plt.subplot(1,3,1)
plt.xlabel('x-value')
plt.ylabel('y-label')
plt.scatter(point_all[0], point_all[1], s=1, c="#ff1212", marker='o')
plt.plot(point1[0], point1[1], color='r', linewidth=1, alpha=0.6)

plt.subplot(1,3,2)
plt.xlabel('x-value')
plt.ylabel('y-label')
plt.scatter(point_all[0], point_all[1], s=1, c="#ff1212", marker='o')
plt.plot(point2[0], point2[1], color='r', linewidth=1, alpha=0.6)

plt.subplot(1,3,3)
plt.xlabel('x-value')
plt.ylabel('y-label')
plt.scatter(point_all[0], point_all[1], s=1, c="#ff1212", marker='o')
plt.plot(point3[0], point3[1], color='r', linewidth=1, alpha=0.6)

plt.show()

import matplotlib
import matplotlib.pyplot as plt
x = [1000,1200,1400,1600,1800,2000,2200,2400,2600,2800,3000]
y1 = [20097.6271,30388.1457,63547.5408,87264.1558,145400.1613,196116.3677,278270.5755,317111.8282,507415.8795,578253.5156,722928.938]

plt.title(u'时间曲线')
plt.xlabel("n")
plt.ylabel("time/ms")
plt.xlim(1000,3000)
plt.plot(x,y1,label="BruteForce")

plt.legend(loc="upper left")
plt.show()
from Board import Board

#Initialize
tttb = Board()

#Start playing.
winned = False
isX = True
while not winned:

    if isX:
        tttb.getNextX()
    else:
        tttb.getNextO()

    tttb.show()

    winned = tttb.checkWin()

    isX = not isX

#Playing ended.
#Here isX should be the one who lose.
if (isX):
    winner = tttb.OPlayer
else:
    winner = tttb.XPlayer

print("Congratulations %s! You have won. " % winner)

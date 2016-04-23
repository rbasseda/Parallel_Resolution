#!/bin/tcsh

#$ -V
#$ -cwd
#$ -N job-0
#$ -e /work/01956/rbasseda/Project_590/PSLDResolutioner_04/job-1.error
#$ -o /work/01956/rbasseda/Project_590/PSLDResolutioner_04/job-1.output
#$ -l h_rt=12:00:00
#$ -q normal
#$ -pe 12way 12
#$ -A TG-CCR120012

set echo


cilkview -plot none -trials all WumpusGame.out 1 ./Data/data-005.txt > answers/output-005.result
sed -i 's/#set/set/g' *.plt
mv Wumpus-result.plt plots/Wumpus-result_05.plt
gnuplot *.plt	
#rm *.plt


#cilkview -plot none -trials all WumpusGame.out 1 ./Data/data-005.txt > answers/output-005.result
#sed -i 's/#set/set/g' *.plt
#mv Wumpus-result.plt plots/Wumpus-result_05.plt
#gnuplot plots/*.plt	
#rm *.plt
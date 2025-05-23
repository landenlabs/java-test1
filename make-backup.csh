#!/bin/csh -f

set dir=~/Dropbox/Java/backups
set fname=TestDate1
set file=`date "+${fname}_%Y-%m-%d-%H-%M.zip"`

find . -name \.DS_Store -print -exec rm {} +
zip -r $dir/$file . -x .git\* -x \*.idea\* -x captures\* -x .gradle\* -x \*build\* -x \*release\* -x \*apk  -x \*backup\* -x target\*

echo 
echo "--- Backups ---"
ls -alrt $dir/${fname}*
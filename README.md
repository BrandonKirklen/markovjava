This is a Markov Chain Sentence Generator.<br/>

I have included the <b>FileFormatter</b> I wrote to help ease the burden of formatting arbitrary texts.<br/>
It isn't the best thing out there and it is still a work in progress,<br/>
But I hope you find it useful and can hack something good out of it.<br/>

To compile: <b>$ make</b><br/>
To run: <b>$ java Markov [num_of_sentences] [format_code] [list_of_files_separated_by_spaces]</b><br/>

The current list of <b>format_codes</b> is as follows:<br/>
<ul>
<li>0: FORMATCODE_NONE: No formatting for sentences</li>
<li>1: FORMATCODE_HTML: Simple HTML trailing page breaks</li>
</ul>

Let me provide you an example of how it would work:<br/>

<b>$ java Markov 10 0 cthulhu.txt</b><br/>
This would generate 10 sentences, unformatted plaintext, using the file "cthulhu.txt" as an input source<br/>

<hr/>
<b>About Input Files:</b><br/>

Preferably, input files are formatted with the following caveats:<br/>
<ul>
<li>One sentence per line</li>
<li>Sentences ending with one of the following delimeters:
    <ul><li>Period (.)</li>
    <li>Comma (,)</li>
    <li>Question Mark (?)</li>
    </ul>
</li></ul>

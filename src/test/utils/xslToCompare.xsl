<!-- remove_stacktrace_and_set_timestamp.xsl -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <!-- Identity transformation rule -->
    <xsl:template match="@*|node()">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()"/>
        </xsl:copy>
    </xsl:template>

    <!-- Modify <Stacktrace> with @timestamp -->
    <xsl:template match="Stacktrace[@timestamp]">
        <Stacktrace timestamp="0">
            <xsl:apply-templates select="node()"/>
        </Stacktrace>
    </xsl:template>

</xsl:stylesheet>

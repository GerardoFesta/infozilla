<!-- remove_stacktrace.xsl -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <!-- Identity transformation rule -->
    <xsl:template match="@*|node()">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()"/>
        </xsl:copy>
    </xsl:template>

    <!-- Exclude <Stacktrace timestamp> element -->
    <xsl:template match="Stacktrace[@timestamp]"/>

</xsl:stylesheet>

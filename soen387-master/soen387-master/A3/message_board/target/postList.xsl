<?xml version = "1.0" encoding ="UTF-8"?>
<xsl:stylesheet version ="1.0" xmlns:xsl ="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
	<html>
	<head>
		<style type="text/css">
			table.tbody {
			border: 1px ;
			}

			td.col {
			border: 1px ;
			background-color: white;
			color: black;
			text-align:right;
			}

			th {
			background-color: #2E9AFE;
			color: white;
			}

		</style>
	</head>
	
	<body>
		<table class="tbody">
			<tr>
				<th style="width:250px">User:</th>
				<th style="width:250px">Post Date:</th>
				<th style="width:250px">Title:</th>
				<th style="width:250px">Content:</th>
				<th style="width:250px">Attachment:</th>
			</tr>
			<xsl:for-each select="PostList/Post">
				<tr>
					<td class="col">
						<xsl:value-of select="name" />
					</td>
					<td class="col">
						<xsl:value-of select="date" />
					</td>
					<td class="col">
						<xsl:value-of select="title" />
					</td>
					<td class="col">
						<xsl:value-of select="content" />
					</td>
					<td class="col">
						<xsl:value-of select="attachment" />
					</td>
				</tr>
			</xsl:for-each>
		</table>
	</body>
	</html>
</xsl:template>
</xsl:stylesheet>
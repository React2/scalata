# scalata
Massive data generator for load tests.

Scalata is a streamming data generator that has the following data __PIPELINE__:

TRANSLATE -> GENERATE -> PARSE -> EXPORT


## Benchmark

* Parser = MongoParser
* Exporter = ConsoleExporter

1M objects ~ 2 minutes [TODO: Improve]

## Configuration

The _template.json_ file is where you must configure the program.

__There are three keys to configure__:

* parser = The parser plugin that transforms the raw data to whatever you want.
* exporter = The exporter plugin will be responsable to export the parsed data to anywhere.
* data_structure = your data definition, class, arrays and fields.

```json
{
    "parser": "MongoParser",
    "exporter": {
        "name": "FileExporter",
        "output": "club-data.js"
    },
    "data_structure": {
        "repeat": 100,
        "fields": [
            {
                "type": "{{int-64}}",
                "name": "timestamp"
            },
            {
                "type": "{{date}}",
                "name": "birthday"
            },
            {
                "type": "{{object}}",
                "name": "club",
                "fields": [
                    {
                        "type": "{{string}}",
                        "name": "name"
                    },
                    {
                        "type": "{{int-32}}",
                        "name": "age"
                    }
                ]
            }
        ]
    }
}

```

### Parsers and Exporters ###

Parsers and Exporters are plugin like. You can write your own plugin and pass it's classpath while running the program.
ex:
```
--parser=your.project.path.XmlParser --exporter=your.project.path.OracleExporter
```

# Run
```
./scalata.sh --definition=data/template.json --parser=your.project.path.XmlParser
```

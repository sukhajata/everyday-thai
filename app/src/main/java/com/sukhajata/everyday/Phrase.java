package com.sukhajata.everyday;



public class Phrase
{
    public  final int pid;
    public final String firstLanguage;
    public final String secondLanguage;
    public final String romanisation;
    public final String literalTranslation;
    public final String notes;
    public final String fileName;
    public Boolean isFavourite;

    public Phrase(int _pid,
                  String _firstLanguage,
                  String _secondLanguage,
                  String _romanisation,
                  String _literalTranslation,
                  String _notes,
                  String _fileName,
                  Boolean _isFavourite)
    {
        pid = _pid;
        firstLanguage = _firstLanguage;
        secondLanguage = _secondLanguage;
        romanisation = _romanisation;
        literalTranslation = _literalTranslation;
        notes = _notes;
        fileName = _fileName;
        isFavourite = _isFavourite;
    }
}

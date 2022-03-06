package com.epam.esm.service.impl;

import static org.mockito.Mockito.mock;

//class TagServiceImplTest {
//    private TagServiceImpl tagService;
//    private TagValidator tagValidator;
//    private TagDuplicationChecker tagDuplicationChecker;
//    private TagDao tagDao;
//    private Tag firstTestTag;
//    private List<Tag> tags;
//
//    @BeforeEach
//    void setUp() {
//        tagValidator = mock(TagValidator.class);
//        tagDuplicationChecker = mock(TagDuplicationChecker.class);
//        tagDao = mock(TagDao.class);
//        tagService = new TagServiceImpl(tagValidator, tagDuplicationChecker, tagDao);
//
//        firstTestTag = new Tag(1, "Jumps");
//        Tag secondTestTag = new Tag(2, "Fly");
//        tags = Arrays.asList(firstTestTag, secondTestTag);
//    }
//
//    @Test
//    void addTagPositiveTest() {
//        when(tagValidator.validateTag(firstTestTag)).thenReturn(true);
//        when(tagDuplicationChecker.checkTagForDuplication(firstTestTag)).thenReturn(true);
//        when(tagDao.add(firstTestTag)).thenReturn(true);
//        when(tagDao.findByName(firstTestTag.getName())).thenReturn(Optional.of(firstTestTag));
//        Assertions.assertEquals(tagService.addTag(firstTestTag), firstTestTag);
//    }
//
//    @Test
//    void addTagWithInvalidTagTest() {
//        when(tagValidator.validateTag(firstTestTag)).thenReturn(false);
//        when(tagDuplicationChecker.checkTagForDuplication(firstTestTag)).thenReturn(true);
//        when(tagDao.add(firstTestTag)).thenReturn(true);
//        when(tagDao.findByName(firstTestTag.getName())).thenReturn(Optional.of(firstTestTag));
//        Assertions.assertThrows(InvalidTagException.class, () -> tagService.addTag(firstTestTag));
//    }
//
//    @Test
//    void addDuplicateTagTest() {
//        when(tagValidator.validateTag(firstTestTag)).thenReturn(true);
//        when(tagDuplicationChecker.checkTagForDuplication(firstTestTag)).thenReturn(false);
//        when(tagDao.add(firstTestTag)).thenReturn(true);
//        when(tagDao.findByName(firstTestTag.getName())).thenReturn(Optional.of(firstTestTag));
//        Assertions.assertThrows(DuplicateTagException.class, () -> tagService.addTag(firstTestTag));
//    }
//
//    @Test
//    void findAllTagsTest() {
//        when(tagDao.findAll()).thenReturn(tags);
//        Assertions.assertEquals(tagService.findAllTags(), tags);
//    }
//
//    @Test
//    void findTagByIdTest() {
//        when(tagDao.findById(1)).thenReturn(Optional.of(firstTestTag));
//        Assertions.assertEquals(tagService.findTagById(1), firstTestTag);
//    }
//
//    @Test
//    void findTagWithInvalidIdTest() {
//        when(tagDao.findById(1)).thenReturn(Optional.empty());
//        Assertions.assertThrows(UnknownTagException.class, () -> tagService.findTagById(1));
//    }
//
//    @Test
//    void findTagByNameTest() {
//        when(tagDao.findByName("Jumps")).thenReturn(Optional.of(firstTestTag));
//        Assertions.assertEquals(tagService.findTagByName("Jumps"), firstTestTag);
//    }
//
//    @Test
//    void findTagWithInvalidNameTest() {
//        when(tagDao.findByName("Jumps")).thenReturn(Optional.empty());
//        Assertions.assertThrows(UnknownTagException.class, () -> tagService.findTagByName("Jumps"));
//    }
//
//    @Test
//    void removeTagByIdPositiveTest() {
//        when(tagDao.findById(1)).thenReturn(Optional.of(firstTestTag));
//        when(tagDao.remove(1)).thenReturn(true);
//        Assertions.assertTrue(tagService.removeTagById(1));
//    }
//
//    @Test
//    void removeTagWithInvalidIdTest() {
//        when(tagDao.findById(1)).thenReturn(Optional.empty());
//        Assertions.assertThrows(UnknownTagException.class ,() -> tagService.removeTagById(1));
//    }
//
//    @Test
//    void updateTagPositiveTest() {
//        firstTestTag.setName("Test");
//        when(tagValidator.validateTag(firstTestTag)).thenReturn(true);
//        when(tagDuplicationChecker.checkTagForDuplication(firstTestTag)).thenReturn(true);
//        when(tagDao.findById(1)).thenReturn(Optional.of(firstTestTag));
//        Assertions.assertEquals(tagService.updateTag(firstTestTag), firstTestTag);
//    }
//
//    @Test
//    void updateTagWithInvalidTagTest() {
//        firstTestTag.setName("");
//        when(tagValidator.validateTag(firstTestTag)).thenReturn(false);
//        when(tagDuplicationChecker.checkTagForDuplication(firstTestTag)).thenReturn(true);
//        when(tagDao.findById(1)).thenReturn(Optional.of(firstTestTag));
//        Assertions.assertThrows(InvalidTagException.class, () -> tagService.updateTag(firstTestTag));
//    }
//
//    @Test
//    void updateTagWithDuplicationTest() {
//        firstTestTag.setName("Fly");
//        when(tagValidator.validateTag(firstTestTag)).thenReturn(true);
//        when(tagDuplicationChecker.checkTagForDuplication(firstTestTag)).thenReturn(false);
//        when(tagDao.findById(1)).thenReturn(Optional.of(firstTestTag));
//        Assertions.assertThrows(DuplicateTagException.class, () -> tagService.updateTag(firstTestTag));
//    }
//}
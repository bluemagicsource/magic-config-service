package org.bluemagic.config.service.utils;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class TagUtilsTest {

	@Test
	public void testReorderSingleTags() {
		
		// CREATE UNORDERED TAGS
		String unorderedTags = "tag2,@user,#hash2,tag1,#hash1";
		
		// RE-ORDER THE TAGS
		String reorderedTags = TagUtils.reorderSingleTags(unorderedTags);
		
		System.out.println("Unordered Tags: " + unorderedTags);
		System.out.println("Reordered Tags: " + reorderedTags);
		
		Assert.assertEquals("#hash1,#hash2,@user,tag1,tag2", reorderedTags);
	}
	
	@Test
	public void testParseTags() {
		
		URI uri = null;
		
		try {
	
			uri = new URI("http://www.config.com/abc?tags%3Dtag2%2C%40user%2C%23hash2%2Ctag1%2C%23hash1%26geo%3Alat%3D30.2%26system%3Dproduction");
		} catch (Throwable t) {
			throw new RuntimeException(t.getMessage(), t);
		}
		
		Map<String, String> tags = TagUtils.parseTags(uri);
		
		System.out.println(tags);
		
		Assert.assertEquals(3, tags.size());
		Assert.assertTrue(tags.containsKey("tags"));
		Assert.assertTrue(tags.containsKey("geo:lat"));
		Assert.assertTrue(tags.containsKey("system"));
		Assert.assertEquals("#hash1,#hash2,@user,tag1,tag2", tags.get("tags"));
		Assert.assertEquals("30.2", tags.get("geo:lat"));
		Assert.assertEquals("production", tags.get("system"));
	}
	
	@Test
	public void testReassemble() {
		
		String base = "http://www.config.com/abc";
		
		Map<String, String> tags = new HashMap<String, String>();
		tags.put("system", "production");
		tags.put("geo:lat", "30.2");
		tags.put("tags", "#hash1,#hash2,@user,tag1,tag2");
		
		String reassembled = TagUtils.reassemble(base, tags);
		
		Assert.assertEquals("http://www.config.com/abc?geo:lat=30.2&system=production&tags=#hash1,#hash2,@user,tag1,tag2", reassembled);
	}
	
	@Test
	public void testRemoveUserFromSingleTags() {
		
		String singleTags = "tag2,@johnny,#hash2,tag1,#hash1";
		
		String singleTagsWithoutUser = TagUtils.removeUserFromSingleTags(singleTags);
		
		Assert.assertEquals("#hash1,#hash2,tag1,tag2", singleTagsWithoutUser);
	}
	
	@Test
	public void testRemoveUserWhenUserIsTheOnlyTag() {
		
		String singleTags = "@johnny";
		
		String singleTagsWithoutUser = TagUtils.removeUserFromSingleTags(singleTags);
		
		Assert.assertTrue(singleTagsWithoutUser.isEmpty());
	}
	
	@Test
	public void testParseUserFromSingleTags() {
		
		String singleTags = "tag2,@johnny,#hash2,tag1,#hash1";
		
		String user = TagUtils.parseUserFromSingleTags(singleTags);
		
		Assert.assertEquals("johnny", user);
	}
	
	@Test
	public void testNoName() {
		
		String singleTags = "tag2,#hash2,tag1,#hash1";
		
		String user = TagUtils.parseUserFromSingleTags(singleTags);
		
		Assert.assertNull(user);
	}
	
	@Test
	public void testBlankName() {
		
		String singleTags = "tag2,@,#hash2,tag1,#hash1";
		
		String user = TagUtils.parseUserFromSingleTags(singleTags);
		
		Assert.assertNull(user);
	}
}

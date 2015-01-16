package de.votesapp.groups;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

	private final GroupRepository groupRepository;

	@Autowired
	public GroupService(final GroupRepository groupRepository) {
		this.groupRepository = groupRepository;
	}

	public Group createOrLoadGroup(String groupId) {

		// TODO: That could be done by overwriting the mongo config like this:
		// @Override
		// public MappingMongoConverter mappingMongoConverter() throws Exception
		// {
		// final MappingMongoConverter mappingMongoConverter =
		// super.mappingMongoConverter();
		// // WhatsApp Keys are containing dots in domains.
		// mappingMongoConverter.setMapKeyDotReplacement("_");
		// return mappingMongoConverter;
		// }
		// But then we need to have one. Now it's autoconfigured
		groupId = groupId.replaceAll("\\.", "_");

		Group existingGroup = groupRepository.findOne(groupId);

		if (existingGroup == null) {
			final Group newGroup = new Group(groupId);
			existingGroup = groupRepository.save(newGroup);
		}

		return existingGroup;
	}

	public Group save(final Group group) {
		return groupRepository.save(group);
	}
}
